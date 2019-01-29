.class public return_in_if_left

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



  invokestatic return_in_if_left/main()I

  pop

  return



.end method



.method public static g()I
.limit locals 1000
.limit stack 1000
iconst_1
ifeq L1
ldc 12
ireturn


goto L0
L1:
ldc 11
ireturn


L0:


ldc 10
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
invokestatic return_in_if_left/g()I
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
