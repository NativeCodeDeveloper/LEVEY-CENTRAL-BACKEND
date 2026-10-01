from pathlib import Path
from PIL import Image, ImageDraw

folder = Path(__file__).parent / "rendered"
files = sorted(folder.glob("pagina-*.jpg"))
groups = [files[i:i + 12] for i in range(0, len(files), 12)]

for group_index, group in enumerate(groups, 1):
    thumb_w, thumb_h = 310, 438
    margin, label_h = 18, 28
    sheet = Image.new("RGB", (4 * (thumb_w + margin) + margin, 3 * (thumb_h + label_h + margin) + margin), "#D9DCE3")
    draw = ImageDraw.Draw(sheet)
    for index, path in enumerate(group):
        image = Image.open(path).convert("RGB")
        image.thumbnail((thumb_w, thumb_h))
        col, row = index % 4, index // 4
        x = margin + col * (thumb_w + margin)
        y = margin + row * (thumb_h + label_h + margin)
        sheet.paste(image, (x, y + label_h))
        draw.text((x, y + 5), f"Página {int(path.stem.split('-')[-1])}", fill="#1D2433")
    sheet.save(folder / f"contacto-{group_index}.jpg", quality=88)
