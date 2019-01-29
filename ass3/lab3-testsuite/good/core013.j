.class public core013

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



  invokestatic core013/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
iconst_0
iconst_1
isub
invokestatic core013/test(I)I
ifeq L0
iconst_0
invokestatic core013/test(I)I
ifeq L0
pop
iconst_1
L0:
invokestatic core013/printBool(I)V


iconst_1
iconst_0
iconst_2
isub
invokestatic core013/test(I)I
ifne L1
iconst_1
invokestatic core013/test(I)I
ifne L1
pop
iconst_0
L1:
invokestatic core013/printBool(I)V


iconst_0
iconst_0
iconst_3
invokestatic core013/test(I)I
ifeq L3
iconst_0
iconst_5
isub
invokestatic core013/test(I)I
ifeq L3
pop
iconst_1
L3:
ifeq L2
iconst_1
ifeq L2
pop
iconst_1
L2:
invokestatic core013/printBool(I)V


iconst_1
iconst_3
invokestatic core013/test(I)I
ifne L4
iconst_0
iconst_0
iconst_5
isub
invokestatic core013/test(I)I
ifeq L5
iconst_1
ifeq L5
pop
iconst_1
L5:
ifne L4
pop
iconst_0
L4:
invokestatic core013/printBool(I)V


iconst_1
invokestatic core013/printBool(I)V


iconst_0
invokestatic core013/printBool(I)V


iconst_0
ireturn


return
.end method
.method public static printBool(I)V
.limit locals 1000
.limit stack 1000
iload_0
ifeq L7
iconst_1
invokestatic Runtime/printInt(I)V




goto L6
L7:
iconst_0
invokestatic Runtime/printInt(I)V




L6:


return
.end method
.method public static test(I)I
.limit locals 1000
.limit stack 1000
iconst_1
iload_0
iconst_0
if_icmpgt L8
pop
iconst_0
L8:
ireturn


return
.end method
