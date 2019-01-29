.class public boolean

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



  invokestatic boolean/main()I

  pop

  return



.end method



.method public static ff()I
.limit locals 1000
.limit stack 1000
iconst_0
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
.method public static tt()I
.limit locals 1000
.limit stack 1000
iconst_1
invokestatic Runtime/printInt(I)V


iconst_1
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_1
istore_0


iconst_0
istore_1


iconst_0
iload_1
ifeq L0
invokestatic boolean/ff()I
ifeq L0
pop
iconst_1
L0:
istore_2


iconst_1
iload_0
ifne L1
invokestatic boolean/ff()I
ifne L1
pop
iconst_0
L1:
istore_3


iconst_0
iload_0
ifeq L2
invokestatic boolean/tt()I
ifeq L2
pop
iconst_1
L2:
istore 4


iconst_1
iload_1
ifne L3
invokestatic boolean/tt()I
ifne L3
pop
iconst_0
L3:
istore 5


iconst_0
ireturn


return
.end method
