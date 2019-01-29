.class public core005

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



  invokestatic core005/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000


ldc 56
istore_1


iconst_1
iload_1
ldc 45
iadd
iconst_2
if_icmple L2
pop
iconst_0
L2:
ifeq L1
iconst_1
dup
istore_0
pop




goto L0
L1:
iconst_2
dup
istore_0
pop




L0:


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
