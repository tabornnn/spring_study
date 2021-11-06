package com.example.demo.login.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspct {

	// 全てのコントローラークラスのメソッドを対象
	// "execution(<戻り値><パッケージ名>.<クラス名>.<メソッド名>(<引数>)”
	//	パッケージ名：「∗..∗」と指定することで、全てのパッケージが対象となります。
	//	クラス名：「∗Controller」と指定することで、末尾にControllerと付くクラスが対象になります。
	//	メソッド名：∗（アスタリスク）を指定します。
	//	引数：..（ドット２個）で全ての引数が対象になります。
	@Before("execution(* *..*.*Controller.*(..))")
	public void startLog(JoinPoint jp) {
		System.out.println("メソッド開始：" + jp.getSignature());
	}
	
    /**
     * コントローラークラスのログ出力用アスペクト.
     */
	// @Controllerが付与されているメソッド全てを対象に実行する
	// Aroundの場合、AOP対象のメソッドを直接実行することで、前後に処理を実行する。
	// メソッドを直接実行しているため、returnには実行結果の戻り値を指定する。
    @Around("@within(org.springframework.stereotype.Controller)")
    public Object startLog(ProceedingJoinPoint jp) throws Throwable {

    	//　実行前の共通処理(Before)
        System.out.println("メソッド開始： " + jp.getSignature());

        try {
            // ポイント２：AOP対象のメソッド実行
            Object result = jp.proceed();

            // 実行後の共通処理
            System.out.println("メソッド終了： " + jp.getSignature());

            return result;

        } catch (Exception e) {
            System.out.println("メソッド異常終了： " + jp.getSignature());
            e.printStackTrace();
            throw e;
        }
    }
}
