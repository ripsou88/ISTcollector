export enum TypeIst {
  Bacterienne = 'BACTERIENNE',
  Virale = 'VIRALE',
  Parasitaire = 'PARASITAIRE',
  Bacterien = 'BACTERIEN',
  Viral = 'VIRAL',
}

export const TypeIstEmoji: Record<TypeIst, { emoji: string, label: string }> = {
  [TypeIst.Bacterienne]: { emoji: '🦠', label: 'Bactérienne' },
  [TypeIst.Virale]: { emoji: '🫟', label: 'Virale' },
  [TypeIst.Parasitaire]: { emoji: '🪱', label: 'Parasitaire' },
  [TypeIst.Bacterien]: { emoji: '🦠', label: 'Bactérien' },
  [TypeIst.Viral]: { emoji: '🫟', label: 'Viral' },
};