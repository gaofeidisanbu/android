.class public Lcom/gaofei/library/GenericTest$User;
.super Ljava/lang/Object;
.source "GenericTest.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/gaofei/library/GenericTest;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "User"
.end annotation


# instance fields
.field private mName:Ljava/lang/String;


# direct methods
.method public constructor <init>(Ljava/lang/String;)V
    .registers 2
    .param p1, "name"    # Ljava/lang/String;

    .prologue
    .line 38
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 39
    iput-object p1, p0, Lcom/gaofei/library/GenericTest$User;->mName:Ljava/lang/String;

    .line 40
    return-void
.end method


# virtual methods
.method public getName()Ljava/lang/String;
    .registers 2

    .prologue
    .line 43
    iget-object v0, p0, Lcom/gaofei/library/GenericTest$User;->mName:Ljava/lang/String;

    return-object v0
.end method
