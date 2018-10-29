using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class Car
    {
        //формат номера: "O115AE 136"
        [Key]
        public string Number { get; set; }

        public string Brand { get; set; }
        public string Model { get; set; }

        //TODO: Добавить доп. параметры авто

        public Guid UserId { get; set; }
        public User User {get; set;}
        public List<ParkingSpaceCar> ParkingSpaceCars { get; set; } = new List<ParkingSpaceCar>();
    }
}
