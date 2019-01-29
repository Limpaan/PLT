.class public return_complex

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



  invokestatic return_complex/main()I

  pop

  return



.end method



.method public static foo(I)I
.limit locals 1000
.limit stack 1000
iconst_1
iload_0
iconst_0
if_icmpeq L2
pop
iconst_0
L2:
ifeq L1
iconst_0
ireturn




goto L0
L1:


L0:


iconst_1
istore_1


iload_0
invokestatic Runtime/printInt(I)V


iload_1
ireturn


return
.end method
.method public static printBool(I)V
.limit locals 1000
.limit stack 1000
iconst_0
ifeq L4


goto L3
L4:
iload_0
ifeq L6
iconst_0
invokestatic Runtime/printInt(I)V




goto L5
L6:
iconst_1
invokestatic Runtime/printInt(I)V




L5:


L3:


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
ldc 42
invokestatic return_complex/foo(I)I
invokestatic return_complex/printBool(I)V


iconst_0
invokestatic return_complex/foo(I)I
invokestatic return_complex/printBool(I)V


iconst_0
ireturn


iconst_1
invokestatic return_complex/printBool(I)V


return
.end method
