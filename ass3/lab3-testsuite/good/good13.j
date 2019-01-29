.class public good13

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



  invokestatic good13/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000


invokestatic Runtime/readInt()I
dup
istore_0
pop


iconst_2
dup
istore_1
pop


L0:
iconst_1
iload_1
iload_0
if_icmple L2
pop
iconst_0
L2:
ifeq L1
iconst_1
istore_2


iconst_2
istore_3


L3:
iconst_0
iconst_1
iload_3
iload_3
imul
iload_1
if_icmple L6
pop
iconst_0
L6:
ifeq L5
iload_2
ifeq L5
pop
iconst_1
L5:
ifeq L4
iconst_1
iload_1
iload_3
idiv
iload_3
imul
iload_1
if_icmpeq L9
pop
iconst_0
L9:
ifeq L8
iconst_0
dup
istore_2
pop




goto L7
L8:


L7:


iload_3
dup
iconst_1
iadd
istore_3
pop




goto L3
L4:
iconst_0
iload_2
ifeq L12
iconst_1
iload_0
iload_1
idiv
iload_1
imul
iload_0
if_icmpeq L13
pop
iconst_0
L13:
ifeq L12
pop
iconst_1
L12:
ifeq L11
iload_1
invokestatic Runtime/printInt(I)V


iload_0
iload_1
idiv
dup
istore_0
pop




goto L10
L11:
iload_1
dup
iconst_1
iadd
istore_1
pop




L10:




goto L0
L1:
iconst_0
ireturn


return
.end method
