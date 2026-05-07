export type TypeIst = 'bacterienne' | 'virale' | 'parasite' | 'bacterien' | 'viral';

export const TypeIstEmoji: Record<TypeIst, { emoji: string; label: string }> = {
  bacterienne: { emoji: '🦠', label: 'Bactérienne' },
  virale: { emoji: '🫟', label: 'Virale' },
  parasite: { emoji: '🪱', label: 'Parasitaire' },
  bacterien: { emoji: '🦠', label: 'Bactérien' },
  viral: { emoji: '🫟', label: 'Viral' },
};
