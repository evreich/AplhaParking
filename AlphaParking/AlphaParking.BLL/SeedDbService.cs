using AlphaParking.DbContext.Models;
using AlphaParking.Models;
using AlphaParking.Models.SeedData;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.BLL
{
    public static class SeedDbService
    {
        public static async Task EnsurePopulated(IApplicationBuilder app)
        {
            using (var serviceScope = app.ApplicationServices.CreateScope())
            {
                var database = serviceScope.ServiceProvider.GetService<AlphaParkingDbContext>();

                using (database)
                {
                    if (!database.Users.Any())
                    {
                        await database.AddRangeAsync(new User[] { UserConstants.Manager });
                        await database.SaveChangesAsync();
                    }

                    if (!database.Cars.Any())
                    {
                        await database.AddRangeAsync(new Car[] { CarConstants.Solaris });
                        await database.SaveChangesAsync();
                    }

                    if (!database.ParkingSpaces.Any())
                    {
                        await database.AddRangeAsync(new ParkingSpace[] { ParkingSpaceConstants.ParkingSpaceOne, ParkingSpaceConstants.ParkingSpaceTwo });
                        await database.SaveChangesAsync();
                    }

                    if (!database.ParkingSpaceCars
                        .Any(elem => elem.CarNumber == CarConstants.Solaris.Number))
                    {
                        await database.AddRangeAsync(new ParkingSpaceCar[]
                        {
                            new ParkingSpaceCar
                            {
                                ParkingSpaceNumber = ParkingSpaceConstants.ParkingSpaceTwo.Number,
                                CarNumber = CarConstants.Solaris.Number,
                                StartParkingTime = new TimeSpan(8, 0, 0),
                                EndParkingTime = new TimeSpan(17, 0, 0)
                            }
                        });
                        await database.SaveChangesAsync();
                    }
                }
            }             
        }
    }
}
