.class public ass_many

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



  invokestatic ass_many/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000


ldc 6
dup
istore_2
dup
istore_1
pop


iload_2
iload_1
iadd
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
