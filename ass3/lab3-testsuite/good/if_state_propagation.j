.class public if_state_propagation

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



  invokestatic if_state_propagation/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000


iconst_1
iconst_1
dup
istore_0
iconst_1
if_icmpeq L2
pop
iconst_0
L2:
ifeq L1


goto L0
L1:


L0:


iload_0
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
