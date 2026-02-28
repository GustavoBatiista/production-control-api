import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' },
});

export const getProducts = () => api.get('/products');
export const getProduct = (id: number) => api.get(`/products/${id}`);
export const createProduct = (data: { code: string; name: string; price: number }) =>
  api.post('/products', data);
export const updateProduct = (id: number, data: { code: string; name: string; price: number }) =>
  api.put(`/products/${id}`, data);
export const deleteProduct = (id: number) => api.delete(`/products/${id}`);
export const getProducibleProducts = () => api.get('/products/producible');

export const getRawMaterials = () => api.get('/raw-materials');
export const getRawMaterial = (id: number) => api.get(`/raw-materials/${id}`);
export const createRawMaterial = (data: { code: string; name: string; stockQuantity: number }) =>
  api.post('/raw-materials', data);
export const updateRawMaterial = (id: number, data: { code: string; name: string; stockQuantity: number }) =>
  api.put(`/raw-materials/${id}`, data);
export const deleteRawMaterial = (id: number) => api.delete(`/raw-materials/${id}`);

export const getProductMaterials = () => api.get('/product-materials');
export const getProductMaterial = (id: number) => api.get(`/product-materials/${id}`);
export const createProductMaterial = (data: { productId: number; rawMaterialId: number; quantityRequired: number }) =>
  api.post('/product-materials', data);
export const updateProductMaterial = (id: number, data: { productId: number; rawMaterialId: number; quantityRequired: number }) =>
  api.put(`/product-materials/${id}`, data);
export const deleteProductMaterial = (id: number) => api.delete(`/product-materials/${id}`);