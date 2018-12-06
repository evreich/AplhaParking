using AlphaParking.BLL.DTO;
using AlphaParking.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL
{
    public interface IParkingSpaceService: ICRUDService<ParkingSpaceDTO>
    {
        void CheckInOnMainParkingSpace(int numParkingSpace, string carNumber);
        void CheckInOnOtherParkingSpace(int numParkingSpace, string carNumber);
        void GiveParkingSpaceToOther(int numParkingSpace, string ownerCarNumber, 
            string otherCarNumber, DateTime startDate, DateTime endDate);

        Task<ParkingSpaceDTO> Get(int numberId);
    }
}
