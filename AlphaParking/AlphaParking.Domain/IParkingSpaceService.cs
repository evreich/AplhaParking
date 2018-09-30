using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Interfaces
{
    public interface IParkingSpaceService: ICRUDService<ParkingSpace>
    {
        void CheckInOnMainParkingSpace(int numParkingSpace, string carNumber);
        void CheckInOnOtherParkingSpace(int numParkingSpace, string carNumber);
        void GiveParkingSpaceToOther(int numParkingSpace, string ownerCarNumber, 
            string otherCarNumber, DateTime startDate, DateTime endDate);

        Task<ParkingSpace> Get(int numberId);
    }
}
