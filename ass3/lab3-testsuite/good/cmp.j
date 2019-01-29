.class public cmp

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



  invokestatic cmp/main()I

  pop

  return



.end method



.method public static printBool(I)V
.limit locals 1000
.limit stack 1000
iload_0
ifeq L1
iconst_1
invokestatic Runtime/printInt(I)V


goto L0
L1:
iconst_0
invokestatic Runtime/printInt(I)V


L0:


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_1
ldc 30
ldc 40
if_icmpeq L2
pop
iconst_0
L2:
invokestatic cmp/printBool(I)V


iconst_1
ldc 30
ldc 30
if_icmpeq L3
pop
iconst_0
L3:
invokestatic cmp/printBool(I)V


iconst_1
ldc 30
ldc 30
if_icmpne L4
pop
iconst_0
L4:
invokestatic cmp/printBool(I)V


iconst_1
ldc 40
ldc 40
if_icmpne L5
pop
iconst_0
L5:
invokestatic cmp/printBool(I)V


iconst_1
iconst_3
iconst_4
if_icmpgt L6
pop
iconst_0
L6:
invokestatic cmp/printBool(I)V


iconst_1
iconst_3
iconst_3
if_icmpgt L7
pop
iconst_0
L7:
invokestatic cmp/printBool(I)V


iconst_1
iconst_4
iconst_3
if_icmplt L8
pop
iconst_0
L8:
invokestatic cmp/printBool(I)V


iconst_1
iconst_4
iconst_4
if_icmplt L9
pop
iconst_0
L9:
invokestatic cmp/printBool(I)V


iconst_1
ldc 400
ldc 300
if_icmpge L10
pop
iconst_0
L10:
invokestatic cmp/printBool(I)V


iconst_1
ldc 400
ldc 400
if_icmpge L11
pop
iconst_0
L11:
invokestatic cmp/printBool(I)V


iconst_1
ldc 400
ldc 300
if_icmple L12
pop
iconst_0
L12:
invokestatic cmp/printBool(I)V


iconst_1
ldc 400
ldc 400
if_icmple L13
pop
iconst_0
L13:
invokestatic cmp/printBool(I)V


iconst_0
ireturn


return
.end method
