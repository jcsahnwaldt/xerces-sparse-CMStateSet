package org.apache.xerces.impl.dtd.models;

public final class CMStateSet
{
  private int fLength;

  private int[] fArray;

  private final int fBitCount;

  public CMStateSet(int bitCount)
  {
    fBitCount = bitCount;
    fArray = new int[2];
    fLength = 0;
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append("[");
    for (int i = 0; i < fLength; i += 2)
    {
      if (i > 0) buf.append(" ");
      buf.append(fArray[i]).append("..").append(fArray[i+1]-1);
    }
    buf.append("]");
    return buf.toString();
  }

  public void intersection(CMStateSet that)
  {
    int pos;
    int ix;

    int[] pos0 = this.fArray;
    int ix0 = 0;
    int len0 = this.fLength;
    boolean thru0 = len0 == 0;

    int[] pos1 = that.fArray;
    int ix1 = 0;
    int len1 = that.fLength;
    boolean thru1 = len1 == 0;

    int[] rpos = new int[2];
    int rix = 0;

    int areas = 0;
    for (;;)
    {
      if (thru0)
      {
        if (thru1)
        {
          break;
        }
        else
        {
          pos = pos1[ix1];
          ix = ix1;
          if (++ix1 == len1) thru1 = true;
        }
      }
      else
      {
        if (thru1)
        {
          pos = pos0[ix0];
          ix = ix0;
          if (++ix0 == len0) thru0 = true;
        }
        else
        {
          int p0 = pos0[ix0];
          int p1 = pos1[ix1];
          if (p0 < p1)
          {
            pos = p0;
            ix = ix0;
            if (++ix0 == len0) thru0 = true;
          }
          else if (p1 < p0)
          {
            pos = p1;
            ix = ix1;
            if (++ix1 == len1) thru1 = true;
          }
          else if ((ix0 & 1) == 0)
          {
            pos = p1;
            ix = ix1;
            if (++ix1 == len1) thru1 = true;
          }
          else
          {
            pos = p0;
            ix = ix0;
            if (++ix0 == len0) thru0 = true;
          }
        }
      }

      if ((ix & 1) == 0)
      {
        if (areas++ == 1)
        {
          if (rix == rpos.length) System.arraycopy(rpos, 0, rpos = new int[rix + 2], 0, rix);
          rpos[rix++] = pos;
        }
      }
      else
      {
        if (--areas == 1)
        {
          rpos[rix++] = pos;
        }
      }

    }

    fLength = rix;
    fArray = rpos;
  }

  public void union(CMStateSet that)
  {
    int pos;
    int ix;

    int[] pos0 = this.fArray;
    int ix0 = 0;
    int len0 = this.fLength;
    boolean thru0 = len0 == 0;

    int[] pos1 = that.fArray;
    int ix1 = 0;
    int len1 = that.fLength;
    boolean thru1 = len1 == 0;

    int[] rpos = new int[2];
    int rix = 0;

    int areas = 0;
    for (;;)
    {
      if (thru0)
      {
        if (thru1)
        {
          break;
        }
        else
        {
          pos = pos1[ix1];
          ix = ix1;
          if (++ix1 == len1) thru1 = true;
        }
      }
      else
      {
        if (thru1)
        {
          pos = pos0[ix0];
          ix = ix0;
          if (++ix0 == len0) thru0 = true;
        }
        else
        {
          pos = pos0[ix0];
          int p1 = pos1[ix1];
          if (pos < p1)
          {
            ix = ix0;
            if (++ix0 == len0) thru0 = true;
          }
          else if (p1 < pos)
          {
            pos = p1;
            ix = ix1;
            if (++ix1 == len1) thru1 = true;
          }
          else if ((ix0 & 1) == 0)
          {
            ix = ix0;
            if (++ix0 == len0) thru0 = true;
          }
          else
          {
            pos = p1;
            ix = ix1;
            if (++ix1 == len1) thru1 = true;
          }
        }
      }

      if ((ix & 1) == 0)
      {
        if (++areas == 1)
        {
          if (rix == rpos.length) System.arraycopy(rpos, 0, rpos = new int[rix + 2], 0, rix);
          rpos[rix++] = pos;
        }
      }
      else
      {
        if (areas-- == 1)
        {
          rpos[rix++] = pos;
        }
      }

    }

    fLength = rix;
    fArray = rpos;
  }

  public void setBit(int index)
  {
    int i = binarySearch(index);
    if ((i & 1) == 1) return;

    int[] array = fArray;
    int length = fLength;
    if (i > 0 && i < length && array[i - 1] == array[i] - 1)
    {
      if ((fLength -= 2) < fArray.length / 3)
      {
        System.arraycopy(array, 0, fArray = new int[fLength + 2], 0, i - 1);
      }
      System.arraycopy(array, i + 1, fArray, i - 1, length - i - 1);
    }
    else if (i > 0 && array[i - 1] == index)
    {
      array[i - 1]++;
    }
    else if (i < length && array[i] - 1 == index)
    {
      array[i]--;
    }
    else
    {
      if ((fLength += 2) > fArray.length)
      {
        System.arraycopy(array, 0, fArray = new int[fLength + 2], 0, i);
      }
      System.arraycopy(array, i, fArray, i + 2, length - i);
      fArray[i] = index;
      fArray[i + 1] = index + 1;
    }
  }

  private int binarySearch(int index)
  {
    int lo = 0;
    int hi = fLength - 1;

    while (lo <= hi)
    {
      int mid = (lo + hi) >> 1;
      int val = fArray[mid];

      if (val < index)
      {
        lo = mid + 1;
      }
      else if (index < val)
      {
        hi = mid - 1;
      }
      else
      {
        lo = mid + 1;
        break;
      }
    }
    return lo;
  }

  public boolean getBit(int index)
  {
    int lo = 0;
    int hi = fLength - 1;
    while (lo <= hi)
    {
      int mid = (lo + hi) >> 1;
      int val = fArray[mid];

      if (val < index)
      {
        lo = mid + 1;
      }
      else if (index < val)
      {
        hi = mid - 1;
      }
      else
      {
        return (mid & 1) == 0;
      }
    }
    return (lo & 1) == 1;
  }

  public boolean isEmpty()
  {
    return fLength == 0;
  }

  public void setTo(CMStateSet that)
  {
    int length = that.fLength;
    fLength = length;
    if (length > fArray.length) fArray = new int[length];
    // TODO: shrink array?
    System.arraycopy(that.fArray, 0, fArray, 0, length);
  }

  public void zeroBits()
  {
    fLength = 0;
    // TODO: shrink array?
  }

  @Override
  public boolean equals(Object o)
  {
    if (! (o instanceof CMStateSet)) return false;
    CMStateSet that = (CMStateSet)o;
    if (this.fBitCount != that.fBitCount) return false;
    if (this.fLength != that.fLength) return false;
    for (int i = fLength; i -- > 0; )
    {
      if (this.fArray[i] != that.fArray[i]) return false;
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    int hash = 61 + fBitCount;
    hash = 37 * hash + fLength;
    for (int i = fLength; i -- > 0; )
    {
      hash = 37 * hash + fArray[i];
    }
    return hash;
  }

}
