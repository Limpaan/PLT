.class public core113

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



  invokestatic core113/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
ldc 45
invokestatic core113/f(I)I
invokestatic Runtime/printInt(I)V


ldc 450
invokestatic core113/f(I)I
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
.method public static f(I)I
.limit locals 1000
.limit stack 1000


iconst_1
iload_0
ldc 100
if_icmplt L2
pop
iconst_0
L2:
ifeq L1
ldc 91
istore_2


iload_2
dup
istore_1
pop




goto L0
L1:
iload_0
dup
istore_1
pop




L0:


iload_1
ireturn


return
.end method
