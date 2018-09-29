using AlphaParking.BLL.Interfaces;
using AlphaParking.DAL.Interfaces;
using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public class ParkingSpaceService : IParkingSpaceService
    {
        private readonly ICRUDRepository<ParkingSpace> _repository;

        public ParkingSpaceService(ICRUDRepository<ParkingSpace> repository)
        {
            _repository = repository;
        }

        public async void CheckInOnMainParkingSpace(int numParkingSpace, string carNumber)
        {
            throw new NotImplementedException();
        }

        public async void CheckInOnOtherParkingSpace(int numParkingSpace, string carNumber)
        {
            throw new NotImplementedException();
        }

        public async void GiveParkingSpaceToOther(int numParkingSpace, string ownerCarNumber, string otherCarNumber, DateTime startDate, DateTime endDate)
        {
            throw new NotImplementedException();
        }

        public async Task<ParkingSpace> Create(ParkingSpace parkingSpace)
        {
            return await _repository.Create(parkingSpace);
        }

        public async Task<ParkingSpace> Delete(ParkingSpace parkingSpace)
        {
            return await _repository.Delete(parkingSpace);
        }

        public async Task<ParkingSpace> Get(int numberId)
        {
            return await _repository.GetElem(elem => elem.Number == numberId);
        }

        public async Task<ParkingSpace> Update(ParkingSpace parkingSpace)
        {
            return await _repository.Update(parkingSpace);
        }

        public Task<ParkingSpace> GetAll()
        {
            throw new NotImplementedException();
        }
    }
}
