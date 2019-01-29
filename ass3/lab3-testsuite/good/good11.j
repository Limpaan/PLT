.class public good11

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



  invokestatic good11/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
istore_0


iconst_0
istore_1




L0:
iconst_1
invokestatic Runtime/readInt()I
dup
istore_2
iconst_0
if_icmpne L2
pop
iconst_0
L2:
ifeq L1
iload_0
iload_2
iadd
dup
istore_0
pop


iload_1
dup
iconst_1
iadd
istore_1
pop




goto L0
L1:
iload_0
iload_1
idiv
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
