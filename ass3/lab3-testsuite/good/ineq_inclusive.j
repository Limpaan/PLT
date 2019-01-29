.class public ineq_inclusive

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



  invokestatic ineq_inclusive/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_1
iconst_0
iconst_0
if_icmpge L2
pop
iconst_0
L2:
ifeq L1
iconst_0
invokestatic Runtime/printInt(I)V


goto L0
L1:
iconst_1
invokestatic Runtime/printInt(I)V


L0:


iconst_1
iconst_1
iconst_1
if_icmpge L5
pop
iconst_0
L5:
ifeq L4
iconst_2
invokestatic Runtime/printInt(I)V


goto L3
L4:
iconst_3
invokestatic Runtime/printInt(I)V


L3:


iconst_1
iconst_2
iconst_2
if_icmple L8
pop
iconst_0
L8:
ifeq L7
iconst_4
invokestatic Runtime/printInt(I)V


goto L6
L7:
iconst_5
invokestatic Runtime/printInt(I)V


L6:


iconst_1
iconst_0
iconst_0
if_icmple L11
pop
iconst_0
L11:
ifeq L10
ldc 6
invokestatic Runtime/printInt(I)V


goto L9
L10:
ldc 7
invokestatic Runtime/printInt(I)V


L9:


iconst_0
ireturn


return
.end method
