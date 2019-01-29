.class public fun_app_two_parameters

.super java/lang/Object



.method public <init>()V

  .limit locals 1



  aload_0

  invokespecial java/lang/Object/<init>()V

  return



.end method



.method public static main([Ljava/lang/String;)V

  .limit locals 1

  .limit stack  1



  invokestatic fun_app_two_parameters/main()I

  pop

  return



.end method



.method public static abc(II)I
.limit locals 1000
.limit stack 1000
iconst_0
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_1
iconst_0
invokestatic fun_app_two_parameters/abc(II)I
ireturn


return
.end method
