using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace AlphaParking.DB.Models
{
    public class User
    {
        [Key]
        public Guid Id { get; set; }

        public string Login { get; set; }
        public string Password { get; set; }
        public string FIO { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }
        //TODO: Добавить доп инфо о пользователе приложения

        public List<UserRole> UserRoles { get; set; } = new List<UserRole>();
        public List<Car> UserCars { get; set; } = new List<Car>();
    }
}
