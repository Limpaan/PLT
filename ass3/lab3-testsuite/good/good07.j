.class public good07

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



  invokestatic good07/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
invokestatic Runtime/readInt()I
istore_0


iload_0
iconst_2
idiv
istore_1


L0:
iconst_1
iload_1
iconst_1
if_icmpgt L2
pop
iconst_0
L2:
ifeq L1
iconst_1
iload_1
iload_0
iload_1
idiv
imul
iload_0
if_icmpeq L5
pop
iconst_0
L5:
ifeq L4
iload_1
invokestatic Runtime/printInt(I)V


goto L3
L4:


L3:


iload_1
dup
iconst_1
isub
istore_1
pop




goto L0
L1:
iconst_0
ireturn


return
.end method
