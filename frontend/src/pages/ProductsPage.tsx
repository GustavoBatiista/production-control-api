import { useEffect, useState } from 'react';
import { getProducts, createProduct, updateProduct, deleteProduct } from '../services/api';
import { getErrorMessage } from '../utils/errorMessage';
import type { Product } from '../types';
import { TableContainer } from '../components/TableContainer';

export function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [editing, setEditing] = useState<Product | null>(null);
  const [formOpen, setFormOpen] = useState(false);
  const [form, setForm] = useState({ code: '', name: '', price: '' });

  const loadProducts = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await getProducts();
      setProducts(res.data);
    } catch (e) {
      setError(getErrorMessage(e));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProducts();
  }, []);

  const openCreate = () => {
    setEditing(null);
    setForm({ code: '', name: '', price: '' });
    setFormOpen(true);
    setError(null);
  };

  const openEdit = (p: Product) => {
    setEditing(p);
    setForm({ code: p.code, name: p.name, price: String(p.price) });
    setFormOpen(true);
    setError(null);
  };

  const closeForm = () => {
    setEditing(null);
    setForm({ code: '', name: '', price: '' });
    setFormOpen(false);
    setError(null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const price = parseFloat(form.price);
    if (isNaN(price)) {
      setError('Please enter a valid price.');
      return;
    }
    setError(null);
    try {
      if (editing) {
        await updateProduct(editing.id, { code: form.code, name: form.name, price });
      } else {
        await createProduct({ code: form.code, name: form.name, price });
      }
      loadProducts();
      closeForm();
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Delete this product?')) return;
    setError(null);
    try {
      await deleteProduct(id);
      loadProducts();
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  return (
    <div>
      <h1 className="page-title">Products</h1>
      <button className="btn" onClick={openCreate}>New Product</button>
      {error && <div className="error-msg">{error}</div>}
      {formOpen && (
        <form className="form-inline" onSubmit={handleSubmit}>
          <input placeholder="Code" value={form.code} onChange={(e) => setForm({ ...form, code: e.target.value })} required />
          <input placeholder="Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
          <input type="number" placeholder="Price" value={form.price} onChange={(e) => setForm({ ...form, price: e.target.value })} required />
          <button type="submit" className="btn">{editing ? 'Update' : 'Create'}</button>
          <button type="button" className="btn btn-secondary" onClick={closeForm}>Cancel</button>
        </form>
      )}
      {loading ? (
        <p className="loading">Loading...</p>
      ) : (
        <TableContainer>
        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Code</th>
              <th>Name</th>
              <th>Price</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {products.map((p) => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.code}</td>
                <td>{p.name}</td>
                <td>{p.price}</td>
                <td>
                  <button className="btn btn-secondary btn-small" onClick={() => openEdit(p)}>Edit</button>
                  <button className="btn btn-danger btn-small" onClick={() => handleDelete(p.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </TableContainer>
      )}
    </div>
  );
}