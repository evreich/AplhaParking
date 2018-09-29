using AlphaParking.DB.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.DAL.Repositories
{
    public class ParkingSpaceRepository: CRUDRepository<ParkingSpace>
    {
        public ParkingSpaceRepository(DbContext dbContext) : base(dbContext)
        {

        }


    }
}
