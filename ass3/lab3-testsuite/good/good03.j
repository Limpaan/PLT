.class public good03

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



  invokestatic good03/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
invokestatic Runtime/readInt()I
istore_0


iconst_1
istore_1


iconst_1
istore_2


L0:
iconst_1
iload_2
iload_0
iconst_1
iadd
if_icmplt L2
pop
iconst_0
L2:
ifeq L1
iload_2
iload_1
imul
dup
istore_1
pop


iload_2
iconst_1
iadd
dup
istore_2
pop




goto L0
L1:
iload_1
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
