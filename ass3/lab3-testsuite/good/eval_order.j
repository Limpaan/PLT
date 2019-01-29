.class public eval_order

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



  invokestatic eval_order/main()I

  pop

  return



.end method



.method public static order(II)I
.limit locals 1000
.limit stack 1000
iconst_0
ireturn


return
.end method
.method public static printIntInt(I)I
.limit locals 1000
.limit stack 1000
iload_0
invokestatic Runtime/printInt(I)V


iload_0
ireturn


return
.end method
.method public static printIntBool(I)I
.limit locals 1000
.limit stack 1000
iload_0
invokestatic Runtime/printInt(I)V


iconst_1
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
invokestatic eval_order/printIntInt(I)I
iconst_1
invokestatic eval_order/printIntInt(I)I
invokestatic eval_order/order(II)I
pop


iconst_2
invokestatic eval_order/printIntInt(I)I
iconst_3
invokestatic eval_order/printIntInt(I)I
iadd
pop


iconst_1
iconst_4
invokestatic eval_order/printIntInt(I)I
iconst_5
invokestatic eval_order/printIntInt(I)I
if_icmpeq L0
pop
iconst_0
L0:
pop


ldc 6
invokestatic eval_order/printIntInt(I)I
ldc 7
invokestatic eval_order/printIntInt(I)I
imul
pop


ldc 8
invokestatic eval_order/printIntInt(I)I
ldc 9
invokestatic eval_order/printIntInt(I)I
idiv
pop


ldc 10
invokestatic eval_order/printIntInt(I)I
ldc 11
invokestatic eval_order/printIntInt(I)I
isub
pop


iconst_1
ldc 12
invokestatic eval_order/printIntInt(I)I
ldc 13
invokestatic eval_order/printIntInt(I)I
if_icmplt L1
pop
iconst_0
L1:
pop


iconst_1
ldc 14
invokestatic eval_order/printIntInt(I)I
ldc 15
invokestatic eval_order/printIntInt(I)I
if_icmpgt L2
pop
iconst_0
L2:
pop


iconst_1
ldc 16
invokestatic eval_order/printIntInt(I)I
ldc 17
invokestatic eval_order/printIntInt(I)I
if_icmple L3
pop
iconst_0
L3:
pop


iconst_1
ldc 18
invokestatic eval_order/printIntInt(I)I
ldc 19
invokestatic eval_order/printIntInt(I)I
if_icmpge L4
pop
iconst_0
L4:
pop


iconst_1
ldc 20
invokestatic eval_order/printIntInt(I)I
ldc 21
invokestatic eval_order/printIntInt(I)I
if_icmpne L5
pop
iconst_0
L5:
pop


iconst_0
ldc 22
invokestatic eval_order/printIntBool(I)I
ifeq L6
ldc 23
invokestatic eval_order/printIntBool(I)I
ifeq L6
pop
iconst_1
L6:
pop


iconst_1
ldc 24
invokestatic eval_order/printIntBool(I)I
ifne L7
ldc 25
invokestatic eval_order/printIntBool(I)I
ifne L7
pop
iconst_0
L7:
pop


iconst_0
ireturn


return
.end method
