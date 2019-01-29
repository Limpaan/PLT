.class public return_in_while

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



  invokestatic return_in_while/main()I

  pop

  return



.end method



.method public static rRrrrRrrrReturn()I
.limit locals 1000
.limit stack 1000
iconst_0
istore_0


L0:
iconst_1
iload_0
dup
iconst_1
iadd
istore_0
iconst_5
if_icmplt L2
pop
iconst_0
L2:
ifeq L1
ldc 71
ireturn




goto L0
L1:
ldc 52
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
invokestatic return_in_while/rRrrrRrrrReturn()I
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
