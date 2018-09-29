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

        //TODO: Добавить доп инфо о пользователе приложения

        public List<UserRoles> UserRoles { get; set; } = new List<UserRoles>();
    }
}
