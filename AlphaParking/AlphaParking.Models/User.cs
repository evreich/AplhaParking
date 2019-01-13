using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace AlphaParking.Models
{
    public class User
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        public string Login {get; set;}

        public string FIO { get; set; }
        public string Phone { get; set; }
        //TODO: Добавить доп инфо о пользователе приложения

        public List<Car> UserCars { get; set; }
    }
}
