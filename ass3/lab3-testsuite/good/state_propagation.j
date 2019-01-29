.class public state_propagation

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



  invokestatic state_propagation/main()I

  pop

  return



.end method



.method public static id(I)I
.limit locals 1000
.limit stack 1000
iload_0
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
istore_0


iload_0
iconst_1
iadd
dup
istore_0
invokestatic state_propagation/id(I)I
pop


iload_0
invokestatic Runtime/printInt(I)V


iload_0
iconst_1
iadd
dup
istore_0
invokestatic state_propagation/id(I)I
pop


iload_0
invokestatic Runtime/printInt(I)V


iload_0
dup
iconst_1
iadd
istore_0
iload_0
dup
iconst_1
iadd
istore_0
iadd
pop


iload_0
invokestatic Runtime/printInt(I)V


iconst_1
iload_0
dup
iconst_1
iadd
istore_0
iload_0
dup
iconst_1
iadd
istore_0
if_icmpeq L0
pop
iconst_0
L0:
pop


iload_0
invokestatic Runtime/printInt(I)V


iconst_1
iload_0
iconst_1
iadd
dup
istore_0
ldc 7
if_icmpeq L3
pop
iconst_0
L3:
ifeq L2
iload_0
invokestatic Runtime/printInt(I)V




goto L1
L2:


L1:


iload_0
invokestatic Runtime/printInt(I)V


iconst_1
iload_0
dup
iconst_1
iadd
istore_0
ldc 8
if_icmpeq L6
pop
iconst_0
L6:
ifeq L5


goto L4
L5:
iload_0
invokestatic Runtime/printInt(I)V




L4:


iload_0
invokestatic Runtime/printInt(I)V


L7:
iconst_1
iload_0
iconst_1
iadd
dup
istore_0
ldc 9
if_icmpeq L9
pop
iconst_0
L9:
ifeq L8
iload_0
invokestatic Runtime/printInt(I)V




goto L7
L8:
iload_0
invokestatic Runtime/printInt(I)V


L10:
iconst_1
iload_0
dup
iconst_1
iadd
istore_0
ldc 11
if_icmpeq L12
pop
iconst_0
L12:
ifeq L11


goto L10
L11:
iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
