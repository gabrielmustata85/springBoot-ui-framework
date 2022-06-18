package com.ui.automation.framework.helpers;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicCompileHelper {
    private final static Logger logger = Logger.getLogger(DynamicCompileHelper.class);
    /**
     * 源代码
     */
    private String source;
    /**
     * Source code
     */
    private String className;
    /**
     * Extract outputPath
     */
    private String outPath = ".";
    /**
     * Extract the name of the packege
     */
    private Pattern packPattern = Pattern.compile("^package\\s+([a-z0-9.]+);");
    /**
     * Extract the name of the class
     */
    private Pattern classNamePattern = Pattern.compile("class\\s+([^{]+)");

    /**
     * Constructor
     *
     * @param source  source code
     * @param outPath outputPath
     */
    public DynamicCompileHelper(String source, String outPath) {
        this.outPath = outPath;
        this.setSource(source);
    }

    /**
     * Eval calculate object.
     *
     * @param stringToEval the string to eval
     * @return the object
     * @throws Exception the exception
     */
    public static Object eval(String stringToEval) throws Exception {
        logger.info("eval:"+stringToEval);
        String methodName = "eval";
        DynamicCompileHelper dynamicComiler = null;
        if(stringToEval.contains("return")) {
            dynamicComiler = new DynamicCompileHelper("package com.qa.util; class AutoCompiler" + DigestUtils
                    .md5Hex(stringToEval).toUpperCase() + " {public static Object eval(){" + stringToEval + "}}", "./target/classes");

        }
        else{
            if(StringHelper.isInteger(stringToEval)) {
                if(stringToEval.length() < 10) {
                    dynamicComiler = new DynamicCompileHelper("package com.qa.util; class AutoCompiler" + DigestUtils
                            .md5Hex(stringToEval).toUpperCase() + " {public static Object eval(){return " + stringToEval + ";}}", "./target/classes");
                }
                else{
                    dynamicComiler = new DynamicCompileHelper("package com.qa.util; class AutoCompiler" + DigestUtils
                            .md5Hex(stringToEval).toUpperCase() + " {public static Object eval(){return " + stringToEval + "L;}}", "./target/classes");
                }
            }
            else if(stringToEval.contains("==") || stringToEval.contains(">") || stringToEval.contains("<") || stringToEval.contains("+") || stringToEval.contains("-") || stringToEval.contains("*") || stringToEval.contains("/")){
                dynamicComiler = new DynamicCompileHelper("package com.qa.util; class AutoCompiler" + DigestUtils
                        .md5Hex(stringToEval).toUpperCase() + " {public static Object eval(){return " + stringToEval + ";}}", "./target/classes");
            }
            else{
                dynamicComiler = new DynamicCompileHelper("package com.qa.util; class AutoCompiler" + DigestUtils
                        .md5Hex(stringToEval).toUpperCase() + " {public static Object eval(){return \"" + stringToEval + "\";}}", "./target/classes");
            }
        }
        return dynamicComiler.Invoke(methodName);
    }

    /**
     * Test entrance
     *
     * @param args parameter list
     * @throws Exception throw exception
     */
    public static void main(String[] args) throws Exception {
        String stringToEval = "(4+3)*8/2";
        String stringToEval2 = "2==2";
        String stringToEval3 = "test";
        String stringToEval4 = "if(200==200){                 return \"约课成功\";                 }                 return \"约课失败\";";
        System.out.println(DynamicCompileHelper.eval(stringToEval));
        System.out.println(DynamicCompileHelper.eval(stringToEval2));
        System.out.println(DynamicCompileHelper.eval(stringToEval3));
        System.out.println(DynamicCompileHelper.eval(stringToEval4));

        Assert.assertEquals(DynamicCompileHelper.eval(stringToEval),28);
        Assert.assertEquals(DynamicCompileHelper.eval(stringToEval2),true);
        Assert.assertEquals(DynamicCompileHelper.eval(stringToEval3),"test");
        Assert.assertEquals(DynamicCompileHelper.eval(stringToEval4),"约课成功");
    }

    /**
     * compile
     *
     * @return compile result true/false
     * @throws Exception
     */
    private boolean doCompile() throws Exception {
        return new InnerCompiler(new URI(className), Kind.SOURCE, this.source).compile();
    }

    /**
     * transfer
     *
     * @param methodName method name
     * @return result
     */
    @SuppressWarnings("unchecked")
    public Object doInvoke(String methodName) {
        ClassLoader classLoader = InnerCompiler.class.getClassLoader();
        try {
            Class classDef = classLoader.loadClass(className);
            Method method = classDef.getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(null);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * automate call
     *
     * @param methodName
     * @return resultObj
     * @throws Exception
     */
    public Object Invoke(String methodName) throws Exception {
        if (this.doCompile()) {
            return doInvoke(methodName);
        }
        return null;
    }

    /**
     * set source code
     *
     * @param source compiled source
     */
    public void setSource(String source) {
        String tmpName = analyseClassName(source);
        this.className = tmpName.trim();
        this.source = source;
    }

    /**
     * analyse Class name
     *
     * @param source
     * @return className
     */
    private String analyseClassName(String source) {
        String tmpName = "";
        Matcher matcher = packPattern.matcher(source);
        if (matcher.find()) {
            tmpName = (matcher.group(1)).concat(".");
        }
        matcher = classNamePattern.matcher(source);
        if (matcher.find()) {
            tmpName = tmpName.concat(matcher.group(1));
        }
        return tmpName;
    }

    /**
     * set Class name
     *
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * set Output path
     *
     * @param outPath
     */
    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DynamicCompile [className=" +
                className +
                ", source=" +
                source +
                "]";
    }

    class InnerCompiler extends SimpleJavaFileObject {
        /**
         * content
         */
        private String content;

        /**
         * Constructor
         *
         * @param uri     uri
         * @param kind    kind
         * @param content content
         */
        public InnerCompiler(URI uri, Kind kind, String content) {
            this(uri, kind);
            this.content = content;
        }

        /**
         * Constructor
         *
         * @param uri
         * @param kind
         */
        protected InnerCompiler(URI uri, Kind kind) {
            super(uri, kind);
        }

        /*
         * (non-Javadoc)
         * @see javax.tools.SimpleJavaFileObject#getCharContent(boolean)
         */
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return this.content;
        }

        /**
         * compile
         *
         * @return result {true|false}
         */
        public boolean compile() {
            boolean result = false;
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> fileObject = Collections.singletonList(this);
            Iterable<String> options = Arrays.asList("-d", outPath);
            CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObject);
            result = task.call();
            return result;
        }
    }
}
