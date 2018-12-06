using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL
{
    public interface ICRUDService<TEntity>: IDisposable where TEntity: class
    {
        Task<TEntity> Create(TEntity elem);
        Task<TEntity> Update(TEntity elem);
        Task<TEntity> Delete(TEntity elem);
        Task<IEnumerable<TEntity>> GetAll();
    }
}
