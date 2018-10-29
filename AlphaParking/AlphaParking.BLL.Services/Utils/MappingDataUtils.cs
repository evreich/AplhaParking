using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services.Utils
{
    internal static class MappingDataUtils
    {
        public static async Task<TDto> WrapperMappingDALFunc<TDto, TModel>
            (Func<TModel, Task<TModel>> DALFunc, TDto elem, IMapper mapper)
        {
            return mapper.Map<TDto>(await DALFunc(mapper.Map<TModel>(elem)));
        }
    }
}
