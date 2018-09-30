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
        private readonly IUnitOfWork _unitOfWork;
        public ParkingSpaceService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async void CheckInOnMainParkingSpace(int numParkingSpace, string carNumber)
        {
            var mainParkingSpace = await _unitOfWork.ParkingSpaceCarRepository.GetElem(x => x.IsMainParkingSpace);
            if (mainParkingSpace.CheckIn)
                throw new Exception("Основное место уже занято");
            mainParkingSpace.CheckIn = true;
            _unitOfWork.Save();
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
            return await _unitOfWork.ParkingSpaceRepository.Create(parkingSpace);
        }

        public async Task<ParkingSpace> Delete(ParkingSpace parkingSpace)
        {
            return await _unitOfWork.ParkingSpaceRepository.Delete(parkingSpace);
        }

        public async Task<ParkingSpace> Get(int numberId)
        {
            return await _unitOfWork.ParkingSpaceRepository.GetElem(elem => elem.Number == numberId);
        }

        public async Task<ParkingSpace> Update(ParkingSpace parkingSpace)
        {
            return await _unitOfWork.ParkingSpaceRepository.Update(parkingSpace);
        }

        public Task<IEnumerable<ParkingSpace>> GetAll()
        {
            throw new NotImplementedException();
        }

        public void Dispose()
        {
            _unitOfWork.Dispose();
        }
    }
}
