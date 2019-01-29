.class public scopes_reuse_name

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



  invokestatic scopes_reuse_name/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
istore_0


iconst_1
ifeq L1
iconst_1
istore_1




goto L0
L1:


L0:


iload_0
invokestatic Runtime/printInt(I)V


iconst_1
ifeq L3


iconst_2
dup
istore_1
pop




goto L2
L3:


L2:


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ifeq L5


goto L4
L5:
iconst_3
istore_1




L4:


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ifeq L7


goto L6
L7:


iconst_4
dup
istore_1
pop




L6:


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
istore_1


L8:
iconst_1
iload_1
dup
iconst_1
iadd
istore_1
iconst_1
if_icmplt L10
pop
iconst_0
L10:
ifeq L9
iconst_5
istore_2




goto L8
L9:
iload_0
invokestatic Runtime/printInt(I)V




iconst_0
istore_1


L11:
iconst_1
iload_1
dup
iconst_1
iadd
istore_1
iconst_1
if_icmplt L13
pop
iconst_0
L13:
ifeq L12


ldc 6
dup
istore_2
pop




goto L11
L12:
iload_0
invokestatic Runtime/printInt(I)V




ldc 7
istore_1




iload_0
invokestatic Runtime/printInt(I)V




ldc 8
dup
istore_1
pop




iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
