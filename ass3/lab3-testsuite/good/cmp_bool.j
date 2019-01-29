.class public cmp_bool

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



  invokestatic cmp_bool/main()I

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
iconst_1
iconst_1
if_icmpeq L2
pop
iconst_0
L2:
invokestatic cmp_bool/printBool(I)V


iconst_1
iconst_1
iconst_0
if_icmpeq L3
pop
iconst_0
L3:
invokestatic cmp_bool/printBool(I)V


iconst_1
iconst_1
iconst_0
if_icmpne L4
pop
iconst_0
L4:
invokestatic cmp_bool/printBool(I)V


iconst_0
ireturn


return
.end method
