.class public redeclare_after_if

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



  invokestatic redeclare_after_if/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_1
iconst_0
iconst_2
if_icmple L2
pop
iconst_0
L2:
ifeq L1
iconst_1
istore_0


goto L0
L1:
iconst_0
pop


L0:


iconst_0
istore_0


iconst_1
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
