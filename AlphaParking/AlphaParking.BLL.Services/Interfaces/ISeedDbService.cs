using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public interface ISeedDbService
    {
        Task EnsurePopulated();
    }
}
