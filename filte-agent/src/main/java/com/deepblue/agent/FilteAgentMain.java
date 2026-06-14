package com.deepblue.agent;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

/**
 * Java Agent 入口
 *
 * 用法: -javaagent:advanced-filted-1.0.0.jar
 *
 * 支持 JVM 参数覆盖过滤规则开关（优先级高于注解 openFlag）:
 *   -Dfilte.white.enabled=false
 *   -Dfilte.black.enabled=false
 *   -Dfilte.welth.enabled=false
 *   -Dfilte.stop.enabled=false
 *   -Dfilte.pause.enabled=false
 *   -Dfilte.check.enabled=false
 *   -Dfilte.supp.enabled=false
 *   -Dfilte.provi.enabled=false
 *   -Dfilte.dupli.enabled=false
 */
public class FilteAgentMain {

    public static void premain(String agentArgs, Instrumentation inst) {
        appendSelf(inst);
    }

    // 支持动态 attach: java -jar attach.jar <pid>
    public static void agentmain(String agentArgs, Instrumentation inst) {
        appendSelf(inst);
    }

    private static void appendSelf(Instrumentation inst) {
        try {
            File agentJar = new File(
                FilteAgentMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            inst.appendToSystemClassLoaderSearch(new JarFile(agentJar));
            System.out.println("[FilteAgent] loaded: " + agentJar.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("[FilteAgent] failed to bootstrap agent jar: " + e.getMessage());
        }
    }
}
