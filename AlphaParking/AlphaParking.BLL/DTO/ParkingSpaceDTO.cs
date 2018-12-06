using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO
{
    public class ParkingSpaceDTO
    {
        public int ParkingSpaceNumber { get; set; }

        List<ParkingSpaceCarDTO> ParkingSpaceCars { get; set; }
    }
}
