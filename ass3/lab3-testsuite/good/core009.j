.class public core009

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



  invokestatic core009/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
invokestatic core009/foo()I
istore_0


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
.method public static foo()I
.limit locals 1000
.limit stack 1000
ldc 10
ireturn


return
.end method
