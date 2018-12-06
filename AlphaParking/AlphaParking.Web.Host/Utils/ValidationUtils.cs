using Microsoft.AspNetCore.Mvc.ModelBinding;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AlphaParking.BLL.Exceptions;
using AlphaParking.Web.Host.ViewModels;

namespace AlphaParking.Web.Host.Utils
{
    static class ValidationUtils
    {
        public static void CheckModelStateValidation(this ModelStateDictionary modelState)
        {
            if (!modelState.IsValid)
            {
                var errorMessage = modelState.Values
                    .SelectMany(v => v.Errors)
                    .Select(err => err.ErrorMessage)
                    .Aggregate((accum, currValue) => accum + currValue + Environment.NewLine);
                throw new ValidationException(errorMessage);
            }
        }

        public static void CheckViewModelNotIsNull(IViewModel vm)
        {
            if (vm == null)
            {
                throw new BadRequestException("Входные данные запроса отсутствуют");
            }
        }
    }
}
