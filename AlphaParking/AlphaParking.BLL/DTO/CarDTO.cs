using AlphaParking.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO
{
    public class CarDTO
    {
        public string Number { get; set; }
        public string Brand { get; set; }
        public string Model { get; set; }

        public int UserId { get; set; }
        public List<ParkingSpaceCarDTO> ParkingSpaceCars { get; set; } = new List<ParkingSpaceCarDTO>();
    }
}
