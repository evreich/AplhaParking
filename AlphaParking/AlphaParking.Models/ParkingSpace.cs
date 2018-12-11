using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace AlphaParking.Models
{
    public class ParkingSpace
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int Number { get; set; }

        public List<ParkingSpaceCar> ParkingSpaceCars { get; set; } = new List<ParkingSpaceCar>();
    }
}
