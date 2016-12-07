using Secretary.Models;

namespace Secretary.Migrations
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<Secretary.Models.SecretaryContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(Secretary.Models.SecretaryContext context)
        {
            //  This method will be called after migrating to the latest version.

            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data. E.g.
            //
            //    context.People.AddOrUpdate(
            //      p => p.FullName,
            //      new Person { FullName = "Andrew Peters" },
            //      new Person { FullName = "Brice Lambson" },
            //      new Person { FullName = "Rowan Miller" }
            //    );
            //
            context.Users.AddOrUpdate(
                p => p.FirstName,
                new User() { FirstName = "Wonho", LastName = "Lee", Password = "test123", Role = Role.ADMIN },
                new User() { FirstName = "Tony", LastName = "Park", Password = "test123", Role = Role.USER }
            );

            context.Subjects.AddOrUpdate(
                new Subject
                {
                    UserId = 1,
                    Code = "MAP524",
                    Room = "S2169",
                    Day = 3,
                    StartHour = 9,
                    StartMin = 50,
                    EndHour = 11,
                    EndMin = 35,
                    Color = 1
                },
                new Subject()
                {
                    UserId = 1,
                    Code = "MAP524",
                    Room = "T3074",
                    Day = 5,
                    StartHour = 15,
                    StartMin = 20,
                    EndHour = 17,
                    EndMin = 5,
                    Color = 1
                },
                new Subject()
                {
                    UserId = 1,
                    Code = "SYS466",
                    Room = "S2174",
                    Day = 2,
                    StartHour = 11,
                    StartMin = 40,
                    EndHour = 13,
                    EndMin = 25,
                    Color = 2
                },
                new Subject()
                {
                    UserId = 1,
                    Code = "SYS466",
                    Room = "T3076",
                    Day = 4,
                    StartHour = 11,
                    StartMin = 40,
                    EndHour = 13,
                    EndMin = 25,
                    Color = 2
                },
                new Subject()
                {
                    UserId = 1,
                    Code = "INT422",
                    Room = "S2123",
                    Day = 3,
                    StartHour = 13,
                    StartMin = 30,
                    EndHour = 15,
                    EndMin = 15,
                    Color = 4
                },
                new Subject()
                {
                    UserId = 1,
                    Code = "INT422",
                    Room = "T3073",
                    Day = 5,
                    StartHour = 13,
                    StartMin = 30,
                    EndHour = 15,
                    EndMin = 15,
                    Color = 4
                },
                new Subject()
                {
                    UserId = 1,
                    Code = "GAM537",
                    Room = "T2108",
                    Day = 2,
                    StartHour = 15,
                    StartMin = 20,
                    EndHour = 17,
                    EndMin = 5,
                    Color = 3
                },
                new Subject()
                {
                    UserId = 1,
                    Code = "GAM537",
                    Room = "T2108",
                    Day = 4,
                    StartHour = 15,
                    StartMin = 20,
                    EndHour = 17,
                    EndMin = 5,
                    Color = 3
                },
                new Subject
                {
                    UserId = 2,
                    Code = "EAC397",
                    Room = "S2153",
                    Day = 2,
                    StartHour = 9,
                    StartMin = 50,
                    EndHour = 10,
                    EndMin = 45,
                    Color = 1
                },
                new Subject
                {
                    UserId = 2,
                    Code = "MAP524",
                    Room = "S2169",
                    Day = 3,
                    StartHour = 9,
                    StartMin = 50,
                    EndHour = 11,
                    EndMin = 35,
                    Color = 1
                },
                new Subject()
                {
                    UserId = 2,
                    Code = "MAP524",
                    Room = "T3074",
                    Day = 5,
                    StartHour = 15,
                    StartMin = 20,
                    EndHour = 17,
                    EndMin = 5,
                    Color = 1
                },
                new Subject()
                {
                    UserId = 2,
                    Code = "SYS366",
                    Room = "S2156",
                    Day = 3,
                    StartHour = 08,
                    StartMin = 00,
                    EndHour = 09,
                    EndMin = 45,
                    Color = 2
                },
                new Subject()
                {
                    UserId = 2,
                    Code = "SYS366",
                    Room = "T2108",
                    Day = 6,
                    StartHour = 11,
                    StartMin = 40,
                    EndHour = 13,
                    EndMin = 25,
                    Color = 2
                },
                new Subject()
                {
                    UserId = 2,
                    Code = "DCN455",
                    Room = "T3076",
                    Day = 3,
                    StartHour = 09,
                    StartMin = 50,
                    EndHour = 11,
                    EndMin = 35,
                    Color = 4
                },
                new Subject()
                {
                    UserId = 2,
                    Code = "DCN455",
                    Room = "T4040",
                    Day = 5,
                    StartHour = 13,
                    StartMin = 30,
                    EndHour = 15,
                    EndMin = 15,
                    Color = 4
                },
                new Subject()
                {
                    UserId = 2,
                    Code = "JAC444",
                    Room = "T2108",
                    Day = 2,
                    StartHour = 08,
                    StartMin = 00,
                    EndHour = 09,
                    EndMin = 45,
                    Color = 3
                }
            );
        }
    }
}
