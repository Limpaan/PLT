.class public core015

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



  invokestatic core015/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
ldc 17
invokestatic core015/ev(I)I
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
.method public static ev(I)I
.limit locals 1000
.limit stack 1000


iconst_1
iload_0
iconst_0
if_icmpgt L2
pop
iconst_0
L2:
ifeq L1
iload_0
iconst_2
isub
invokestatic core015/ev(I)I
dup
istore_1
pop


goto L0
L1:
iconst_1
iload_0
iconst_0
if_icmplt L5
pop
iconst_0
L5:
ifeq L4
iconst_0
dup
istore_1
pop


goto L3
L4:
iconst_1
dup
istore_1
pop


L3:


L0:


iload_1
ireturn


return
.end method
