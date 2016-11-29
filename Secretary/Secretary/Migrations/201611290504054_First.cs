namespace Secretary.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class First : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Subject",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.Int(nullable: false),
                        Code = c.String(),
                        Room = c.String(),
                        Day = c.Int(nullable: false),
                        StartHour = c.Int(nullable: false),
                        StartMin = c.Int(nullable: false),
                        EndHour = c.Int(nullable: false),
                        EndMin = c.Int(nullable: false),
                        Color = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.User", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.User",
                c => new
                    {
                        UserId = c.Int(nullable: false, identity: true),
                        FirstName = c.String(),
                        LastName = c.String(),
                        Password = c.String(),
                        Role = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.UserId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Subject", "UserId", "dbo.User");
            DropIndex("dbo.Subject", new[] { "UserId" });
            DropTable("dbo.User");
            DropTable("dbo.Subject");
        }
    }
}
