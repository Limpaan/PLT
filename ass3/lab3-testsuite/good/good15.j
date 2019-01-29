.class public good15

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



  invokestatic good15/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_1
istore_0


iload_0
invokestatic Runtime/printInt(I)V


iconst_1
iconst_1
ifne L0
iconst_1
iload_0
dup
iconst_1
iadd
istore_0
ldc 45
if_icmpne L1
pop
iconst_0
L1:
ifne L0
pop
iconst_0
L0:
pop


iload_0
invokestatic Runtime/printInt(I)V


iconst_1
iconst_0
ifne L2
iconst_1
iload_0
dup
iconst_1
iadd
istore_0
iconst_0
if_icmpge L3
pop
iconst_0
L3:
ifne L2
pop
iconst_0
L2:
pop


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
iconst_1
ifeq L4
iconst_1
iload_0
dup
iconst_1
iadd
istore_0
iconst_0
if_icmplt L5
pop
iconst_0
L5:
ifeq L4
pop
iconst_1
L4:
pop


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
iconst_0
ifeq L6
iconst_1
iload_0
dup
iconst_1
iadd
istore_0
iconst_0
if_icmpgt L7
pop
iconst_0
L7:
ifeq L6
pop
iconst_1
L6:
pop


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
istore_1


iconst_0
iconst_1
ldc 34
ldc 6
if_icmplt L11
pop
iconst_0
L11:
ifeq L10
iconst_1
iload_1
iconst_0
if_icmplt L12
pop
iconst_0
L12:
ifeq L10
pop
iconst_1
L10:
ifeq L9
iload_0
invokestatic Runtime/printInt(I)V




goto L8
L9:
ldc 42
invokestatic Runtime/printInt(I)V




L8:


iconst_0
ireturn


return
.end method
