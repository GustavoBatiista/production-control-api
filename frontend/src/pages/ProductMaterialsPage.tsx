import { useEffect, useState } from 'react';
import {
  getProductMaterials,
  createProductMaterial,
  updateProductMaterial,
  deleteProductMaterial,
  getProducts,
  getRawMaterials,
} from '../services/api';
import { getErrorMessage } from '../utils/errorMessage';
import type { ProductMaterial, Product, RawMaterial } from '../types';
import { TableContainer } from '../components/TableContainer';

export function ProductMaterialsPage() {
  const [associations, setAssociations] = useState<ProductMaterial[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [rawMaterials, setRawMaterials] = useState<RawMaterial[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [editing, setEditing] = useState<ProductMaterial | null>(null);
  const [formOpen, setFormOpen] = useState(false);
  const [form, setForm] = useState({ productId: '', rawMaterialId: '', quantityRequired: '' });

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [assocRes, prodRes, matRes] = await Promise.all([
        getProductMaterials(),
        getProducts(),
        getRawMaterials(),
      ]);
      setAssociations(assocRes.data);
      setProducts(prodRes.data);
      setRawMaterials(matRes.data);
    } catch (e) {
      setError(getErrorMessage(e));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const openCreate = () => {
    setEditing(null);
    setForm({ productId: '', rawMaterialId: '', quantityRequired: '' });
    setFormOpen(true);
    setError(null);
  };

  const openEdit = (pm: ProductMaterial) => {
    setEditing(pm);
    setForm({
      productId: String(pm.productId),
      rawMaterialId: String(pm.rawMaterialId),
      quantityRequired: String(pm.quantityRequired),
    });
    setFormOpen(true);
    setError(null);
  };

  const closeForm = () => {
    setEditing(null);
    setForm({ productId: '', rawMaterialId: '', quantityRequired: '' });
    setFormOpen(false);
    setError(null);
  };

  const isDuplicate = (productId: number, rawMaterialId: number) => {
    const exists = associations.some(
      (a) => a.productId === productId && a.rawMaterialId === rawMaterialId
    );
    if (editing) {
      return exists && (editing.productId !== productId || editing.rawMaterialId !== rawMaterialId);
    }
    return exists;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const productId = parseInt(form.productId, 10);
    const rawMaterialId = parseInt(form.rawMaterialId, 10);
    const quantityRequired = parseInt(form.quantityRequired, 10);
    if (isNaN(productId) || isNaN(rawMaterialId) || isNaN(quantityRequired)) {
      setError('Please fill all fields with valid values.');
      return;
    }
    if (quantityRequired < 1) {
      setError('Quantity must be at least 1.');
      return;
    }
    if (isDuplicate(productId, rawMaterialId)) {
      setError('This product–raw material association already exists.');
      return;
    }
    setError(null);
    try {
      if (editing) {
        await updateProductMaterial(editing.id, { productId, rawMaterialId, quantityRequired });
      } else {
        await createProductMaterial({ productId, rawMaterialId, quantityRequired });
      }
      loadData();
      closeForm();
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Delete this association?')) return;
    setError(null);
    try {
      await deleteProductMaterial(id);
      loadData();
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  const getProductName = (id: number) => products.find((p) => p.id === id)?.name ?? `#${id}`;
  const getRawMaterialName = (id: number) => rawMaterials.find((r) => r.id === id)?.name ?? `#${id}`;

  return (
    <div>
      <h1 className="page-title">Associations</h1>
      <button className="btn" onClick={openCreate}>New Association</button>
      {error && <div className="error-msg">{error}</div>}
      {formOpen && (
        <form className="form-inline" onSubmit={handleSubmit}>
          <select value={form.productId} onChange={(e) => setForm({ ...form, productId: e.target.value })} required>
            <option value="">Select Product</option>
            {products.map((p) => (
              <option key={p.id} value={p.id}>{p.code} - {p.name}</option>
            ))}
          </select>
          <select value={form.rawMaterialId} onChange={(e) => setForm({ ...form, rawMaterialId: e.target.value })} required>
            <option value="">Select Raw Material</option>
            {rawMaterials.map((r) => (
              <option key={r.id} value={r.id}>{r.code} - {r.name}</option>
            ))}
          </select>
          <input type="number" min="1" placeholder="Quantity Required" value={form.quantityRequired} onChange={(e) => setForm({ ...form, quantityRequired: e.target.value })} required />
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
              <th>Product</th>
              <th>Raw Material</th>
              <th>Quantity Required</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {associations.map((a) => (
              <tr key={a.id}>
                <td>{a.id}</td>
                <td>{getProductName(a.productId)}</td>
                <td>{getRawMaterialName(a.rawMaterialId)}</td>
                <td>{a.quantityRequired}</td>
                <td>
                  <button className="btn btn-secondary btn-small" onClick={() => openEdit(a)}>Edit</button>
                  <button className="btn btn-danger btn-small" onClick={() => handleDelete(a.id)}>Delete</button>
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