.class public core019

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



  invokestatic core019/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
ldc 78
istore_0


iconst_1
istore_1


iload_1
invokestatic Runtime/printInt(I)V




iload_0
invokestatic Runtime/printInt(I)V


L0:
iconst_1
iload_0
ldc 76
if_icmpgt L2
pop
iconst_0
L2:
ifeq L1
iload_0
dup
iconst_1
isub
istore_0
pop


iload_0
invokestatic Runtime/printInt(I)V


ldc 7
istore_1


iload_1
invokestatic Runtime/printInt(I)V




goto L0
L1:
iload_0
invokestatic Runtime/printInt(I)V


iconst_1
iload_0
iconst_4
if_icmpgt L5
pop
iconst_0
L5:
ifeq L4
iconst_4
istore_1


iload_1
invokestatic Runtime/printInt(I)V




goto L3
L4:


L3:


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
